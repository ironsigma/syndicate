<?php
namespace Cyniris\SyndicateBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity
 * @ORM\Table(name="feed_update")
 */
class Update {
    /**
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     * @ORM\Column(type="integer")
     */
    protected $id;

    /**
     * @ORM\Column(type="datetime")
     */
    protected $updated;

    /**
     * @ORM\Column(type="integer", name="total_count")
     */
    protected $totalCount;

    /**
     * @ORM\Column(type="integer", name="new_count")
     */
    protected $newCount;

    /**
     * @ORM\ManyToOne(targetEntity="Feed")
     */
    protected $feed;

    /**
     * Get id
     *
     * @return integer 
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set updated
     *
     * @param \DateTime $updated
     * @return Update
     */
    public function setUpdated($updated)
    {
        $this->updated = $updated;

        return $this;
    }

    /**
     * Get updated
     *
     * @return \DateTime 
     */
    public function getUpdated()
    {
        return $this->updated;
    }

    /**
     * Set totalCount
     *
     * @param integer $totalCount
     * @return Update
     */
    public function setTotalCount($totalCount)
    {
        $this->totalCount = $totalCount;

        return $this;
    }

    /**
     * Get totalCount
     *
     * @return integer 
     */
    public function getTotalCount()
    {
        return $this->totalCount;
    }

    /**
     * Set newCount
     *
     * @param integer $newCount
     * @return Update
     */
    public function setNewCount($newCount)
    {
        $this->newCount = $newCount;

        return $this;
    }

    /**
     * Get newCount
     *
     * @return integer 
     */
    public function getNewCount()
    {
        return $this->newCount;
    }

    /**
     * Set feed
     *
     * @param \Cyniris\SyndicateBundle\Entity\Feed $feed
     * @return Update
     */
    public function setFeed(\Cyniris\SyndicateBundle\Entity\Feed $feed = null)
    {
        $this->feed = $feed;

        return $this;
    }

    /**
     * Get feed
     *
     * @return \Cyniris\SyndicateBundle\Entity\Feed 
     */
    public function getFeed()
    {
        return $this->feed;
    }
}
